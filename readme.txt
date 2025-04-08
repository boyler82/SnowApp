❄️ Aplikacja “SnowTask” – zarządzanie zleceniami odśnieżania

📌 1. Wprowadzenie

SnowTask to aplikacja umożliwiająca mieszkańcom miast Norwegii zgłaszanie potrzeby odśnieżania (np. chodników, podjazdów, parkingów) oraz łączenie tych zleceń z firmami lub osobami prywatnymi, które chcą je wykonać.

System będzie zintegrowany z mapą (np. Google Maps lub OpenStreetMap), pozwalał na lokalizowanie zgłoszeń, przypisywanie wykonawców oraz zarządzanie statusem zadania. W przyszłości możliwa będzie integracja z platformami społecznościowymi (Facebook, Google) w celu łatwiejszego logowania oraz promocji.

⸻

⚙️ 2. Technologie

Backend:
	•	Java 17
	•	Spring Boot
	•	Spring Web (REST API)
	•	Spring Data JPA (Hibernate)
	•	Spring Security (w przyszłości do logowania)
	•	MySQL lub PostgreSQL
	•	Lombok
	•	Obsługa map/location (Geocoding API)

Frontend:
	•	React + Vite
	•	TailwindCSS (lub prosty Bootstrap na start)
	•	Integracja z mapą (Google Maps API lub Leaflet/OpenStreetMap)

Inne:
	•	Postman / Insomnia – testowanie API
	•	MySQL Workbench – praca z bazą danych
	•	n8n – integracje (np. Facebook, AI analiza zgłoszeń, powiadomienia)

⸻

🔌 3. Główne funkcje aplikacji

Funkcja	Opis
🔹 Zgłoszenie zadań	Formularz dla mieszkańca – wybór lokalizacji, opis, zdjęcie
🔹 Lokalizacja na mapie	Zlecenia widoczne na mapie z ikonami
🔹 Rejestracja użytkowników	Logowanie przez email / Facebook / Google (w przyszłości)
🔹 Panel wykonawcy	Lista zleceń do podjęcia w okolicy
🔹 Panel administratora	Zarządzanie zleceniami, statusami, użytkownikami
🔹 Status zadania	np. “oczekuje”, “w realizacji”, “zakończone”
🔹 Historia i oceny	System opinii po wykonaniu zadania



⸻

🗘 4. Integracje (planowane)

Narzędzie / API	Cel
🗘 Google Maps API	Lokalizacja zleceń, geokodowanie
📱 Facebook Graph API	Logowanie, autoryzacja
🧠 OpenAI API	Analiza opisów zgłoszeń (np. priorytet, kategoria)
✉️ n8n	Automatyczne powiadomienia e-mail / webhooki
📦 Supabase / Airtable	MVP z użyciem gotowej bazy (opcjonalnie)



⸻

🔄 5. Przykładowe endpointy REST API

Metoda	Endpoint	Opis
POST	/v1/reports	Zgłoszenie zadania
GET	/v1/reports	Lista zadań z filtrami
POST	/v1/users/register	Rejestracja użytkownika
POST	/v1/users/login	Logowanie
PUT	/v1/reports/{id}/accept	Podjęcie zlecenia
PUT	/v1/reports/{id}/complete	Oznaczenie jako zakończone
GET	/v1/reports/nearby	Zlecenia w okolicy



⸻

🧪 6. Testowanie i narzędzia developerskie
	•	Postman – testy endpointów
	•	JUnit 5 + Mockito – testy jednostkowe
	•	H2 / Docker – baza danych lokalna
	•	Railway / Render – hosting backendu
	•	Vercel – hosting frontendu

⸻

💡 7. Możliwe rozszerzenia
	•	Powiadomienia push / SMS
	•	Obsługa zdjęć i wideo w zgłoszeniu
	•	System powiadomień dla wykonawców (np. nowa oferta w okolicy)
	•	Panel miejskiego administratora
	•	Cennik usług + mikropłatności
	•	Ranking wykonawców i statystyki
	•	Historia zadań na mapie

⸻

✅ 8. Podsumowanie

SnowTask to nowoczesna platforma wspierająca miasta i społeczności lokalne w reagowaniu na potrzeby związane z odśnieżaniem.
Łączy mieszkańców, wykonawców i technologie mapowe oraz AI, by proces ten był szybki, efektywny i przejrzysty.
System jest modułowy i może być łatwo rozbudowywany o nowe funkcje, kanały integracji oraz automatyzacje.

=== Folder Structure ===

backend/
└── src/
    ├── main/
    │   ├── java/
    │   │   └── no/
    │   │       └── snowtask/
    │   │           ├── controller/
    │   │           ├── service/
    │   │           ├── repository/
    │   │           ├── model/
    │   │           ├── dto/
    │   │           ├── config/
    │   │           └── SnowTaskApplication.java
    │   └── resources/
    │       ├── application.yml
    │       └── static/
    └── test/

frontend/
├── public/
└── src/
    ├── components/
    ├── pages/
    ├── api/
    ├── hooks/
    ├── types/
    ├── App.tsx
    └── main.tsx

docker/

docs/