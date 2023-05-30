# Documentazione delle API NobusWare x Youtube

Le API consentono agli sviluppatori di accedere a informazioni e contenuti di Youtube. 

Di seguito sono elencate le API disponibili insieme ai relativi endpoint, parametri ed esempi di richieste.

## 1. MAKE SEARCH ON YOUTUBE

Questa API restituisce una lista di video con un determinato nome o simili.

**Endpoint:** `api/v1/find`

**Parametri:**
- `criteria` (stringa, obbligatorio): Il nome del video da cercare.

**Esempio di richiesta:**
```sh
GET api/v1/find?criteria=pewdiepie%20congratulations
```


## 2. DOWNLOAD FROM INDEX OR VIDEOID

Questa API restituisce i post dell'utente in base al nome utente fornito.

**Endpoint:** `api/v1/download`

**Parametri:**
- `index` (stringa, non obbligatorio): Dopo aver effettuato la ricerca attraverso l'api precedente a ogni video trovato viene assegnato un index.
- `videoID` (stringa, non obbligatorio): Il codice univoco che youtube associa a un video solitamente trovabile in url.

**Esempio di richiesta:**
```sh
GET /api/v1/download?index=0
GET /api/v1/download?videoID=rLoGGMbF-YQ
```


## VIDEO DIMOSTRATIVO DEL API
- `https://youtu.be/3OqudK2O5D0`
