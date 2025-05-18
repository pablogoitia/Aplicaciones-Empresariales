import { Component } from '@angular/core';

@Component({
  selector: 'app-footer',
  standalone: true,
  template: `
    <footer class="footer">
        <div class="footer-left">
            <p><b>&copy; 2025 Polaflix.</b> Todos los derechos reservados.</p>
        </div>
        <div class="footer-right">
            <a href="/" title="Términos y condiciones">Términos y condiciones</a>
            <a href="/" title="Política de privacidad">Política de privacidad</a>
            <a href="/" title="Contáctanos">Contáctanos</a>
        </div>
    </footer>
  `,
  styleUrls: ['./footer.component.css'],
})
export class FooterComponent { }