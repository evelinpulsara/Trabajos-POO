import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-clientes',
  templateUrl: './clientes.component.html',
  styleUrls: ['./clientes.component.css']
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];
  clienteForm: FormGroup;
  editingCliente: any = null;
  showModal = false;

  constructor(private apiService: ApiService, private fb: FormBuilder) {
    this.clienteForm = this.fb.group({
      nombre: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadClientes();
  }

  loadClientes(): void {
    this.apiService.getClientes().subscribe({
      next: (data) => {
        this.clientes = data;
      },
      error: (err) => {
        console.error('Error al cargar clientes:', err);
      }
    });
  }

  openModal(cliente?: any): void {
    this.editingCliente = cliente || null;
    if (cliente) {
      this.clienteForm.patchValue(cliente);
    } else {
      this.clienteForm.reset();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.editingCliente = null;
  }

  saveCliente(): void {
    if (this.clienteForm.valid) {
      if (this.editingCliente) {
        this.apiService.updateCliente(this.editingCliente.id, this.clienteForm.value).subscribe({
          next: () => {
            this.loadClientes();
            this.closeModal();
          },
          error: (err) => {
            console.error('Error al actualizar cliente:', err);
          }
        });
      } else {
        this.apiService.createCliente(this.clienteForm.value).subscribe({
          next: () => {
            this.loadClientes();
            this.closeModal();
          },
          error: (err) => {
            console.error('Error al crear cliente:', err);
          }
        });
      }
    }
  }

  deleteCliente(id: number): void {
    if (confirm('¿Está seguro de eliminar este cliente?')) {
      this.apiService.deleteCliente(id).subscribe({
        next: () => {
          this.loadClientes();
        },
        error: (err) => {
          console.error('Error al eliminar cliente:', err);
        }
      });
    }
  }
}
