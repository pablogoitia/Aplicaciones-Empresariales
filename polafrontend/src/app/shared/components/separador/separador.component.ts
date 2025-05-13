import { Component, Input } from '@angular/core';

@Component({
  selector: 'separador',
  standalone: true,
  template: `
    <div [style.height.px]="height"></div>
  `,
})
export class SeparadorComponent {
  height: number = 20; // Altura por defecto del separador

  @Input() set inputHeight(value: number) {
    this.height = value;
  }
}