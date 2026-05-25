import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { Opcion } from '../../models/opcion.model';

@Component({
  selector: 'app-menu-item',
  templateUrl: './menu-item.component.html',
  styleUrls: ['./menu-item.component.css']
})
export class MenuItemComponent {
  @Input() opcion!: Opcion;
  @Input() nivel = 0;
  @Input() sidebarCollapsed = false;
  expandido = false;

  constructor(private router: Router) { }

  toggleExpandido(): void {
    if (this.sidebarCollapsed) {
      return; // No expandir en modo colapsado
    }
    if (this.tieneHijos()) {
      this.expandido = !this.expandido;
    }
  }

  navegar(): void {
    if (this.opcion.ruta && !this.tieneHijos()) {
      this.router.navigate([this.opcion.ruta]);
    }
  }

  tieneHijos(): boolean {
    return this.opcion.hijos && this.opcion.hijos.length > 0;
  }

  getPaddingLeft(): string {
    return this.sidebarCollapsed ? '0px' : `${this.nivel * 16}px`;
  }
}
