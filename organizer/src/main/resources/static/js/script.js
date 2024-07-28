console.log("Script load");

document.addEventListener('DOMContentLoaded', function () {
    let theme_current = getTheme();
    applyTheme(theme_current);
//for dark light theme button
    const button = document.querySelector('#themeB1');
    if (button) {
        button.addEventListener("click", () => {
            const oldTheme = theme_current;
            theme_current = theme_current === "dark" ? "light" : "dark";
            setTheme(theme_current);
            applyTheme(theme_current, oldTheme);
            updateButtonText(theme_current);
        });
    }

    function applyTheme(newTheme, oldTheme = null) {
        if (oldTheme) {
            document.querySelector('html').classList.remove(oldTheme);
        }
        document.querySelector('html').classList.add(newTheme);
    }

 
    function setTheme(theme) {
        localStorage.setItem("theme", theme);
    }

    function getTheme() {
        let theme = localStorage.getItem("theme");
        return theme ? theme : "light";
    }
});
