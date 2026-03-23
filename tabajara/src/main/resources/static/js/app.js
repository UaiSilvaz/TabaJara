document.addEventListener('DOMContentLoaded', () => {
  const root = document.documentElement;
  const toggleBtn = document.getElementById('theme-toggle');
  const storedTheme = localStorage.getItem('tabajaraTheme') || 'dark';

  const updateToggleLabel = (theme) => {
    if (!toggleBtn) {
      return;
    }

    toggleBtn.textContent = theme === 'light' ? 'Ativar modo escuro' : 'Ativar modo claro';
    toggleBtn.setAttribute('aria-pressed', theme === 'light');
  };

  const setTheme = (theme) => {
    root.setAttribute('data-theme', theme);
    updateToggleLabel(theme);
  };

  setTheme(storedTheme);

  if (toggleBtn) {
    toggleBtn.addEventListener('click', () => {
      const nextTheme = root.getAttribute('data-theme') === 'light' ? 'dark' : 'light';
      setTheme(nextTheme);
      localStorage.setItem('tabajaraTheme', nextTheme);
    });
  }
});
