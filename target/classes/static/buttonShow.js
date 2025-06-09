document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.show-btn').forEach(function(button) {
        button.addEventListener('click', function() {
            // Если кнопка disabled — ничего не делаем!
            if (this.disabled) return;

            // Логика переключения
            document.querySelectorAll('.show').forEach(function(show) {
                show.style.display = 'none';
            });
            document.querySelectorAll('.show-btn').forEach(function(btn) {
                btn.style.display = 'inline-block';
            });

            const show = this.closest('form').querySelector('.show');
            show.style.display = 'block';
            this.style.display = 'none';
        });
    });
});