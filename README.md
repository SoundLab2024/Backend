# Backend
Java Springboot application and Database for the app.

---

**Deploy in Azure**
1. Effettuare il login in Azure da CLI: `az login`
2. Creare il gruppo di risorse se non esiste: `az group create --name soundlab --location eastus`
3. Creare il Cluster Kubernetes: az aks create `--resource-group soundlab --name cluster --node-count 1 --enable-addons monitoring --generate-ssh-keys`
4. Ottieni le credenziali per permettere a kubectl di connettersi al cluster: `az aks get-credentials --resource-group soundlab --name soundlabCluster`
5. Fare l'apply del ConfigMap e PersistentVolumeClaim per pgAdmin (assicurarsi di essere nella directory /deploy):
  `kubectl apply -f pgadmin-cm0-configmap.yaml &&
   kubectl apply -f pgadmin-persistentvolumeclaim.yaml`
6. Fare l'apply degli YAML per creare i Deployment e Service (assicurarsi di essere nella directory /deploy):
  `kubectl apply -f db-deployment.yaml &&
   kubectl apply -f db-service.yaml  kubectl &&
   apply -f pgadmin-deployment.yaml &&
   kubectl apply -f pgadmin-service.yaml &&
   kubectl apply -f server-deployment.yaml && 
   kubectl apply -f server-service.yaml`



--In caso di modifiche all'app springboot:
1. Buildare l'immagine docker.
2. Pusharla su docker hub.
3. Modificare il file /deploy/server-deployment.yaml sostituendo il nome dell'immagine vecchia alla nuova.
4. Eliminare il cluster esistente: `az aks delete --name cluster --resource-group soundlab`
5. Seguire la guida precedente dal punto 3.

---








