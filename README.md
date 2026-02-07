# 📚 Fantasy World Scraper & Analyzer

> **A high-performance asynchronous web scraper built with Node.js, coupled with a strict Java domain analyzer.**

## 📖 Overview

This project is a dual-stack solution designed to harvest and analyze fantasy literature data. It automates the collection of thousands of book records from *fantasy-worlds.org* and provides a Java-based architecture for processing and filtering that data.

The core of this project is the **Intelligent Parser**, designed to handle thousands of pages concurrently without triggering server bans.

---

## 🕷️ The Parser (Node.js)

**Location:** `parser/парсер.js`

The scraper is engineered for speed and reliability. It crawls pages 1 through 2070 to extract book metadata including titles, authors, ratings, and vote counts.



### Key Features

* **🚀 High Concurrency** – Uses `Promise.all` to process multiple pages simultaneously.
* **⏱️ Hz Batching Strategy** – Implements a `CONCURRENCY` limit of 5 and a `BATCH_DELAY` of 800ms to respect server rate limits.
* **📊 Visual Feedback** – Integrated `cli-progress` bar to track real-time scraping status.
* **💪 Robust Extraction** – Utilizes `cheerio` (jQuery for Node) to parse complex DOM structures and clean data on the fly.

### Code Snippet: Batch Processing

```javascript
// Smart batching to prevent IP bans
for (let p = START_PAGE; p <= END_PAGE; p += CONCURRENCY) {
    const batch = [];
    for (let i = 0; i < CONCURRENCY && p + i <= END_PAGE; i++) {
        batch.push(parsePage(p + i));
    }
    await Promise.all(batch);
    await sleep(BATCH_DELAY); // Politeness delay
}
```

---

## ☕ The Processor (Java)

**Location:** `src/main/java`

Once the data is collected, the Java application handles the business logic, enrichment, and filtering.

* **📂 CSV Ingestion** – Custom `CsvBookReader` to parse raw text files into `Book` domain objects.
* **🔎 Stream Filtering** – Uses Java Streams to filter books by minimum rating thresholds.
* **🌐 API Simulation** – The `BookEnrichmentService` mocks external calls to Google Books to "enrich" local data with fresh ratings.

---

## 🛠️ Tech Stack

| Component   | Technology     | Description                     |
| ----------- | -------------- | ------------------------------- |
| **Scraper** | **Node.js**    | Runtime environment             |
|             | `axios`        | HTTP Client for fetching HTML   |
|             | `cheerio`      | DOM parsing and data extraction |
|             | `cli-progress` | Terminal progress bar           |
| **Backend** | **Java 25**    | Core application language       |
|             | Maven          | Dependency management           |
|             | Stream API     | Functional data processing      |

---

## 🚀 Usage

### 1️⃣ Run the Parser

Collect the data first.

```bash
cd parser
npm install axios cheerio cli-progress
node парсер.js
```

*Output: Generates `fantasy_worlds_books.csv`*

### 2️⃣ Run the Java App

Process the data.

```bash
mvn clean install
mvn exec:java -Dexec.mainClass="Main"
```

*Note: Ensure the CSV file path in `Main.java` matches your generated file.*

---

## 📂 Project Structure

```text
.
├── 🕷️ parser/
│   ├── 🧠 парсер.js                # Main scraping logic
│   ├── 💾 fantasy_worlds_books.csv # Scraped data
│   └── 🎨 assets/
│       └── 🖼 scraper-demo.gif       # Optional GIF preview
├── ☕ src/main/java/
│   ├── 📑 csv/                     # Data Ingestion
│   ├── 📚 domain/                  # POJOs (Book.java)
│   ├── ⚙️ filter/                  # Business Rules
│   ├── 🌐 google/                  # API Clients
│   └── 🚀 Main.java                # Entry Point
└── 🧩 pom.xml                      # Maven Config
```


