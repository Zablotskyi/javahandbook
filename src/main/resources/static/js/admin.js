function insertAtCursor(textarea, text) {
    const start = textarea.selectionStart;
    const end = textarea.selectionEnd;

    const before = textarea.value.substring(0, start);
    const after = textarea.value.substring(end);

    textarea.value = before + text + after;

    const newCursorPosition = start + text.length;
    textarea.selectionStart = newCursorPosition;
    textarea.selectionEnd = newCursorPosition;

    textarea.focus();
}

function getEditor() {
    return document.getElementById("content");
}

function insertHeading() {
    const textarea = getEditor();

    const text = `
<h2>Новий заголовок</h2>
`;

    insertAtCursor(textarea, text);
}

function insertParagraph() {
    const textarea = getEditor();

    const text = `
<p>
    Текст нового абзацу.
</p>
`;

    insertAtCursor(textarea, text);
}

function insertCodeBlock() {
    const textarea = getEditor();

    const text = `
<pre><code>public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, Java!");
    }
}</code></pre>
`;

    insertAtCursor(textarea, text);
}

function insertImage() {
    const textarea = getEditor();

    const text = `
<img src="/uploads/example.png" alt="Опис зображення">
`;

    insertAtCursor(textarea, text);
}

function transliterateToLatin(text) {
    const map = {
        "а": "a",
        "б": "b",
        "в": "v",
        "г": "h",
        "ґ": "g",
        "д": "d",
        "е": "e",
        "є": "ie",
        "ж": "zh",
        "з": "z",
        "и": "y",
        "і": "i",
        "ї": "i",
        "й": "i",
        "к": "k",
        "л": "l",
        "м": "m",
        "н": "n",
        "о": "o",
        "п": "p",
        "р": "r",
        "с": "s",
        "т": "t",
        "у": "u",
        "ф": "f",
        "х": "kh",
        "ц": "ts",
        "ч": "ch",
        "ш": "sh",
        "щ": "shch",
        "ь": "",
        "ю": "iu",
        "я": "ia",
        "ы": "y",
        "э": "e",
        "ё": "e",
        "ъ": ""
    };

    return text
        .toLowerCase()
        .split("")
        .map(function (char) {
            return map[char] || char;
        })
        .join("");
}

function createSlug(text) {
    return transliterateToLatin(text)
        .toLowerCase()
        .trim()
        .replace(/[^a-z0-9]+/g, "-")
        .replace(/^-+/, "")
        .replace(/-+$/, "");
}

function generateSlugFromTitle() {
    const titleInput = document.getElementById("title");
    const slugInput = document.getElementById("slug");

    if (!titleInput || !slugInput) {
        return;
    }

    slugInput.value = createSlug(titleInput.value);
}

document.addEventListener("DOMContentLoaded", function () {
    const titleInput = document.getElementById("title");
    const slugInput = document.getElementById("slug");

    if (!titleInput || !slugInput) {
        return;
    }

    titleInput.addEventListener("input", function () {
        slugInput.value = createSlug(titleInput.value);
    });
});