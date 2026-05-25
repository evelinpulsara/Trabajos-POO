import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-opciones-admin',
  templateUrl: './opciones-admin.component.html',
  styleUrls: ['./opciones-admin.component.css']
})
export class OpcionesAdminComponent implements OnInit {
  opciones: any[] = [];
  opcionForm: FormGroup;
  editingOpcion: any = null;
  showModal = false;

  constructor(private apiService: ApiService, private fb: FormBuilder) {
    this.opcionForm = this.fb.group({
      nombre: ['', Validators.required],
      padre_opcion_id: [null],
      ruta: [''],
      icono: [''],
      orden: [0],
      activo: [true]
    });
  }

  ngOnInit(): void {
    this.loadOpciones();
  }

  loadOpciones(): void {
    this.apiService.getOpciones().subscribe({
      next: (data) => {
        this.opciones = data;
      },
      error: (err) => {
        console.error('Error al cargar opciones:', err);
      }
    });
  }

  openModal(opcion?: any): void {
    this.editingOpcion = opcion || null;
    if (opcion) {
      this.opcionForm.patchValue(opcion);
    } else {
      this.opcionForm.reset({ activo: true, orden: 0 });
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.editingOpcion = null;
  }

  saveOpcion(): void {
    if (this.opcionForm.valid) {
      if (this.editingOpcion) {
        this.apiService.updateOpcion(this.editingOpcion.id, this.opcionForm.value).subscribe({
          next: () => {
            this.loadOpciones();
            this.closeModal();
          },
          error: (err) => {
            console.error('Error al actualizar opción:', err);
          }
        });
      } else {
        this.apiService.createOpcion(this.opcionForm.value).subscribe({
          next: () => {
            this.loadOpciones();
            this.closeModal();
          },
          error: (err) => {
            console.error('Error al crear opción:', err);
          }
        });
      }
    }
  }

  deleteOpcion(id: number): void {
    if (confirm('¿Está seguro de eliminar esta opción?')) {
      this.apiService.deleteOpcion(id).subscribe({
        next: () => {
          this.loadOpciones();
        },
        error: (err) => {
          console.error('Error al eliminar opción:', err);
        }
      });
    }
  }
}
