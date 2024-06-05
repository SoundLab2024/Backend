package com.soundlab;

import com.soundlab.domain.Listening;
import com.soundlab.domain.Song;
import com.soundlab.domain.User;
import com.soundlab.domain.properties.SongType;
import com.soundlab.dto.records.AddRemoveListeningDTO;
import com.soundlab.service.ListeningService;
import com.soundlab.utils.response.Payload;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import com.soundlab.repository.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = SoundlabApplication.class)
class SoundlabApplicationTests {

	@Autowired
	private ListeningService listeningService;

	@MockBean
	private UserRepository userRepository;

	@MockBean
	private SongRepository songRepository;

	@MockBean
	private ListeningRepository listeningRepository;

	@MockBean
	private PasswordEncoder encoder;

	@Test
	public void testInsertListeningSuccess() {
		// Creo e setto gli oggetti fittizzi
		User mockUser = new User();
		mockUser.setEmail("mock.user@mail.com");
		mockUser.setUsername("mockUser");
		mockUser.setPassword(encoder.encode("password"));
		Song mockSong = new Song();
		mockSong.setId(1L);
		mockSong.setTitle("mockSong");
		mockSong.setYear(2000);
		mockSong.setGenre("Genere");
		mockSong.setType(SongType.ORIGINAL);

		// Li cerco nei miei repositori fittizzi
		when(userRepository.findById("mock.user@mail.com")).thenReturn(Optional.of(mockUser));
		when(songRepository.findById(1L)).thenReturn(Optional.of(mockSong));

		AddRemoveListeningDTO dto = new AddRemoveListeningDTO("mock.user@mail.com", 1L);

		// Mando in esecuzione il test vero e proprio
		Payload result = listeningService.insertListening(dto);

		// Verifico i miei output
		assertEquals(HttpStatus.OK.value(), result.statusCode());
		assertEquals("Canzone ascoltata con successo", result.msg());

		// Verifico nella mia repository
		verify(userRepository).findById("mock.user@mail.com");
		verify(songRepository).findById(1L);
		verify(listeningRepository).save(any(Listening.class));
	}



}
