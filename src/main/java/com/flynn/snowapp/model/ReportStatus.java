package com.flynn.snowapp.model;

public enum ReportStatus {
    PENDING,       // ✅ Zgłoszenie utworzone – czeka na zainteresowanego wykonawcę
    ASSIGNED,      // 🕒 Wykonawca wybrał zgłoszenie – oczekuje na zatwierdzenie przez zleceniodawcę
    IN_PROGRESS,   // 🔨 Zgłoszenie zatwierdzone i realizowane
    DONE           // ✅ Zlecenie zakończone – oznaczone przez zleceniodawcę jako wykonane
}
