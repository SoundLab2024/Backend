package com.soundlab;

import com.soundlab.domain.*;
import com.soundlab.domain.properties.Gender;
import com.soundlab.domain.properties.Role;
import com.soundlab.domain.properties.SongType;
import com.soundlab.dto.records.AddRemoveListeningDTO;
import com.soundlab.dto.records.AddRemoveSongDTO;
import com.soundlab.dto.records.CredentialsDTO;
import com.soundlab.dto.records.RegistrationDTO;
import com.soundlab.security.JWTService;
import com.soundlab.service.AuthenticationService;
import com.soundlab.service.ListeningService;
import com.soundlab.service.PlaylistService;
import com.soundlab.utils.response.Payload;
import com.soundlab.utils.response.UserPayload;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.soundlab.repository.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SoundlabApplicationTests {

	@Autowired
	private ListeningService listeningService;
	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private AuthenticationService authenticationService;

	@MockBean
	private JWTService jwtService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private SongRepository songRepository;
	@MockBean
	private ListeningRepository listeningRepository;
	@MockBean
	private PlaylistRepository playlistRepository;
	@MockBean
	private LibraryRepository libraryRepository;
	@MockBean
	private PasswordEncoder encoder;

	private User mockUser;
	private Song mockSong;
	private Playlist mockPlay;
	private User user;

	@BeforeEach
	void setUp() {
		// Inizializzazione degli oggetti mock
		mockUser = new User();
		mockUser.setEmail("mock.user@mail.com");
		mockUser.setUsername("mockUser");
		mockUser.setPassword(encoder.encode("password"));

		mockSong = new Song();
		mockSong.setId(1L);
		mockSong.setTitle("mockSong");
		mockSong.setYear(2000);
		mockSong.setGenre("Genere");
		mockSong.setType(SongType.ORIGINAL);
		mockSong.setPlaylists(new ArrayList<>());

		mockPlay = new Playlist();
		mockPlay.setId(1L);
		mockPlay.setName("mockPlaylist");
		mockPlay.setSongsNumber(1);
		mockPlay.setSongs(new ArrayList<>());
		mockPlay.getSongs().add(mockSong);

		mockSong.getPlaylists().add(mockPlay);


		// Configurazione dei mock
		when(userRepository.findById("mock.user@mail.com")).thenReturn(Optional.of(mockUser));
		when(songRepository.findById(1L)).thenReturn(Optional.of(mockSong));
		when(playlistRepository.findById(1L)).thenReturn(Optional.of(mockPlay));

		user = new User();
		user.setEmail("test@example.com");
		user.setPassword("oldPassword");
		when(userRepository.findById("test@example.com")).thenReturn(Optional.of(user));
	}

	@Test
	public void testInsertOneListening() {
		AddRemoveListeningDTO dto = new AddRemoveListeningDTO(mockUser.getEmail(), mockSong.getId());
		Payload result = listeningService.insertListening(dto);

		assertEquals(HttpStatus.OK.value(), result.statusCode());
		assertEquals("Canzone ascoltata con successo", result.msg());

		verify(userRepository).findById("mock.user@mail.com");
		verify(songRepository).findById(1L);
		verify(listeningRepository).save(any(Listening.class));

	}

	@Test
	public void testRemoveSongFromPlaylist(){
		AddRemoveSongDTO dto = new AddRemoveSongDTO(mockPlay.getId(), mockSong.getId());
		Payload result = playlistService.removeSong(dto);

		assertEquals(HttpStatus.OK.value(), result.statusCode());
		assertEquals("Canzone rimossa con successo", result.msg());
		assertEquals(0, mockPlay.getSongsNumber());

		verify(playlistRepository).findById(1L);
		verify(songRepository).findById(1L);
		verify(playlistRepository).save(any(Playlist.class));

	}

	@Test
	public void testRegistration(){

		CredentialsDTO credentialsDTO = new CredentialsDTO("test@example.com", "password", "testUser", "Male", Date.valueOf("1990-01-01"));

		User user = User.builder()
				.email(credentialsDTO.email())
				.password("encodedPassword")
				.username(credentialsDTO.username())
				.role(Role.USER)
				.birth(credentialsDTO.birth())
				.gender(Gender.Male)
				.active(true)
				.build();

		Library library = Library.builder()
				.user(user)
				.playlistsNumber(0)
				.build();

		when(userRepository.findById(credentialsDTO.email())).thenReturn(Optional.empty());
		when(encoder.encode(credentialsDTO.password())).thenReturn("encodedPassword");
		when(userRepository.save(any(User.class))).thenReturn(user);
		when(libraryRepository.save(any(Library.class))).thenReturn(library);
		when(jwtService.generateToken(user)).thenReturn("jwtToken");

		UserPayload result = authenticationService.register(credentialsDTO);

		assertEquals(HttpStatus.OK.value(), result.statusCode());
		assertEquals("jwtToken", result.token());
		assertEquals(user.getUsername(), result.email());
		assertEquals(user.getName(), result.username());
		assertEquals(user.getRole().toString(), result.role());
		assertEquals(library.getId(), result.libraryId());

	}

	@Test
	public void testChangePassword_UserNotFound() { // Test user non trovato che quindi mi ritorna un optional vuoto
		when(userRepository.findById("test@example.com")).thenReturn(Optional.empty());
		RegistrationDTO registrationDTO = new RegistrationDTO("test@example.com", "oldPassword", "newPassword");

		Payload result = authenticationService.changePw(registrationDTO);

		assertEquals(HttpStatus.CONFLICT.value(), result.statusCode());
		assertEquals("Impossibile completare la richiesta. I dati specificati non sono presenti nel sistema", result.msg());
	}

	@Test
	public void testChangePassword_InvalidOldPassword() { // Test password vecchia invalida per qualche motivo specificato nella funzione
		when(encoder.matches(anyString(), anyString())).thenReturn(false);
		RegistrationDTO registrationDTO = new RegistrationDTO("test@example.com", "oldPassword", "newPassword");

		Payload result = authenticationService.changePw(registrationDTO);

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.statusCode());
		assertEquals("Impossibile completare la richiesta. I dati specificati non sono validi", result.msg());
	}

	@Test
	public void testChangePassword_OldEqualsNewPassword() {
		when(encoder.matches("newPassword", user.getPassword())).thenReturn(true);
		RegistrationDTO registrationDTO = new RegistrationDTO("test@example.com", "newPassword", "newPassword");

		Payload result = authenticationService.changePw(registrationDTO);

		assertEquals(HttpStatus.BAD_REQUEST.value(), result.statusCode());
		assertEquals("Impossibile completare la richiesta. I dati specificati non sono validi", result.msg());
	}

	@Test
	public void testChangePassword_Success() {
		when(encoder.matches("oldPassword", user.getPassword())).thenReturn(true);
		when(encoder.encode("newPassword")).thenReturn("encodedNewPassword");
		RegistrationDTO registrationDTO = new RegistrationDTO("test@example.com", "oldPassword", "newPassword");

		Payload result = authenticationService.changePw(registrationDTO);

		assertEquals(HttpStatus.OK.value(), result.statusCode());
		assertEquals("Operazione completata con successo", result.msg());
		verify(userRepository, times(1)).save(user);
	}


}
