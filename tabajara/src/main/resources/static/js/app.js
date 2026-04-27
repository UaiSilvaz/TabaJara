document.addEventListener('DOMContentLoaded', () => {
  setupMobileNav();
  setupPasswordToggles();
  setupFormLoading();
  setupLiveValidation();
  setupPasswordStrength();
});

function setupMobileNav() {
  const toggle = document.querySelector('[data-nav-toggle]');
  const nav = document.querySelector('[data-nav-links]');

  if (!toggle || !nav) {
    return;
  }

  toggle.addEventListener('click', () => {
    const isOpen = nav.classList.toggle('is-open');
    toggle.setAttribute('aria-expanded', String(isOpen));
  });
}

function setupPasswordToggles() {
  const toggles = document.querySelectorAll('[data-toggle-password]');

  toggles.forEach((button) => {
    const inputId = button.getAttribute('data-toggle-password');
    const input = document.getElementById(inputId);

    if (!input) {
      return;
    }

    button.addEventListener('click', () => {
      const nextType = input.type === 'password' ? 'text' : 'password';
      input.type = nextType;

      const icon = button.querySelector('i');
      if (icon) {
        icon.className = nextType === 'password' ? 'fa-regular fa-eye' : 'fa-regular fa-eye-slash';
      }

      button.setAttribute('aria-label', nextType === 'password' ? 'Mostrar senha' : 'Ocultar senha');
    });
  });
}

function setupFormLoading() {
  const forms = document.querySelectorAll('form[data-loading-text]');

  forms.forEach((form) => {
    form.addEventListener('submit', () => {
      const submitButton = form.querySelector('button[type="submit"]');
      if (!submitButton) {
        return;
      }

      submitButton.dataset.originalText = submitButton.textContent;
      submitButton.textContent = form.getAttribute('data-loading-text') || 'Enviando...';
      submitButton.classList.add('is-loading');
      submitButton.disabled = true;
    });
  });
}

function setupLiveValidation() {
  const forms = document.querySelectorAll('form.js-validate');

  forms.forEach((form) => {
    const inputs = form.querySelectorAll('input, select, textarea');

    inputs.forEach((input) => {
      const events = ['input', 'blur', 'change'];
      events.forEach((eventName) => {
        input.addEventListener(eventName, () => validateField(input));
      });
    });

    form.addEventListener('submit', (event) => {
      let firstInvalid = null;
      inputs.forEach((input) => {
        const valid = validateField(input);
        if (!valid && !firstInvalid) {
          firstInvalid = input;
        }
      });

      if (firstInvalid) {
        event.preventDefault();
        firstInvalid.focus();
      }
    });
  });
}

function validateField(input) {
  if (input.type === 'hidden' || input.disabled) {
    return true;
  }

  const field = input.closest('.field');
  if (!field) {
    return true;
  }

  let message = '';

  if (input.hasAttribute('required') && !String(input.value).trim()) {
    message = 'Campo obrigatorio.';
  }

  if (!message && input.type === 'email' && input.value.trim()) {
    const emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailPattern.test(input.value.trim())) {
      message = 'Digite um email valido.';
    }
  }

  const minLength = Number(input.getAttribute('minlength'));
  if (!message && minLength > 0 && input.value.length > 0 && input.value.length < minLength) {
    message = `Use pelo menos ${minLength} caracteres.`;
  }

  const matchWith = input.getAttribute('data-match-with');
  if (!message && matchWith) {
    const other = document.getElementById(matchWith);
    if (other && input.value !== other.value) {
      message = 'Os valores nao conferem.';
    }
  }

  const checkbox = input.type === 'checkbox';
  if (!message && checkbox && input.required && !input.checked) {
    message = 'Voce precisa aceitar para continuar.';
  }

  const feedback = getOrCreateFeedback(field);
  const isValid = !message;

  field.classList.toggle('has-error', !isValid);
  field.classList.toggle('has-success', isValid && input.value.trim().length > 0);
  input.setAttribute('aria-invalid', String(!isValid));
  feedback.textContent = message;

  return isValid;
}

function getOrCreateFeedback(field) {
  let feedback = field.querySelector('.field-feedback');
  if (!feedback) {
    feedback = document.createElement('div');
    feedback.className = 'field-feedback';
    field.appendChild(feedback);
  }

  return feedback;
}

function setupPasswordStrength() {
  const passwordInputs = document.querySelectorAll('input[data-strength-fill]');

  passwordInputs.forEach((input) => {
    const fillId = input.getAttribute('data-strength-fill');
    const labelId = input.getAttribute('data-strength-label');
    const fill = document.getElementById(fillId);
    const label = document.getElementById(labelId);

    if (!fill || !label) {
      return;
    }

    const update = () => {
      const value = input.value;
      const score = calculatePasswordScore(value);
      const visual = getStrengthVisual(score);

      fill.style.width = `${visual.width}%`;
      fill.style.backgroundColor = visual.color;
      label.textContent = `Forca da senha: ${visual.label}`;
    };

    input.addEventListener('input', update);
    update();
  });
}

function calculatePasswordScore(password) {
  let score = 0;

  if (password.length >= 8) score += 1;
  if (password.length >= 12) score += 1;
  if (/[A-Z]/.test(password)) score += 1;
  if (/[a-z]/.test(password)) score += 1;
  if (/\d/.test(password)) score += 1;
  if (/[^A-Za-z0-9]/.test(password)) score += 1;

  return score;
}

function getStrengthVisual(score) {
  if (score <= 2) {
    return { width: 33, color: '#ff6b8a', label: 'fraca' };
  }

  if (score <= 4) {
    return { width: 66, color: '#fbbf24', label: 'media' };
  }

  return { width: 100, color: '#34d399', label: 'forte' };
}
