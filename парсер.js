const fs = require("fs");
const axios = require("axios");
const cheerio = require("cheerio");
const cliProgress = require("cli-progress");

const BASE_URL = "https://fantasy-worlds.org/lib/";
const START_PAGE = 1;
const END_PAGE = 2070;

const CONCURRENCY = 5;      // 🔥 сколько страниц параллельно
const BATCH_DELAY = 800;    // пауза между батчами

const OUTPUT = "fantasy_worlds_books.csv";

const headers = {
    "User-Agent": "Mozilla/5.0 (FantasyWorldsBot/1.1)"
};

const sleep = ms => new Promise(r => setTimeout(r, ms));

// CSV header
fs.writeFileSync(
    OUTPUT,
    "title;author;rating;votes;url;page\n",
    "utf8"
);

const bar = new cliProgress.SingleBar(
    { format: "Парсинг |{bar}| {value}/{total} страниц" },
    cliProgress.Presets.shades_classic
);

bar.start(END_PAGE - START_PAGE + 1, 0);

async function parsePage(page) {
    try {
        const { data } = await axios.get(`${BASE_URL}${page}`, { headers });
        const $ = cheerio.load(data);
        let rows = "";

        $('table').has('a[href^="/lib/id"]').each((_, table) => {
            const block = $(table);

            const link = block.find('a[href^="/lib/id"]').first();
            const title = link.text().trim();
            if (!title.includes("—")) return;

            const url = "https://fantasy-worlds.org" + link.attr("href");

            let author = "";
            block.find('a[href^="/author/"]').first().each((_, a) => {
                author = $(a).text().trim();
            });

            let rating = "";
            let votes = "";
            const match = block.text().match(/Рейтинг:\s*([\d.]+)\/(\d+)/);
            if (match) {
                rating = match[1];
                votes = match[2];
            }

            rows += [
                title,
                author,
                rating,
                votes,
                url,
                page
            ].map(v => `"${v || ""}"`).join(";") + "\n";
        });

        fs.appendFileSync(OUTPUT, rows, "utf8");
    } catch (e) {
        console.error(`❌ Страница ${page}: ${e.message}`);
    } finally {
        bar.increment();
    }
}

(async () => {
    for (let p = START_PAGE; p <= END_PAGE; p += CONCURRENCY) {
        const batch = [];
        for (let i = 0; i < CONCURRENCY && p + i <= END_PAGE; i++) {
            batch.push(parsePage(p + i));
        }
        await Promise.all(batch);
        await sleep(BATCH_DELAY);
    }

    bar.stop();
    console.log(`\n✅ Готово! CSV: ${OUTPUT}`);
})();
