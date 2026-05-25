import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Opcion } from '../../models/opcion.model';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
  menuOpciones: Opcion[] = [];
  sidebarCollapsed = false;
  loading = true;

  @Output() onToggle = new EventEmitter<boolean>();
  @Output() onLogout = new EventEmitter<void>();

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.cargarMenu();
  }

  cargarMenu(): void {
    this.apiService.getOpcionesMenu().subscribe({
      next: (opciones) => {
        this.menuOpciones = opciones;
        this.loading = false;
      },
      error: (error) => {
        console.error('Error al cargar el menú:', error);
        this.loading = false;
      }
    });
  }

  toggleSidebar(): void {
    this.sidebarCollapsed = !this.sidebarCollapsed;
    this.onToggle.emit(this.sidebarCollapsed);
  }

  logout(): void {
    this.onLogout.emit();
  }
}
