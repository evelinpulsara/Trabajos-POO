import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ApiService } from '../../services/api.service';

@Component({
  selector: 'app-productos',
  templateUrl: './productos.component.html',
  styleUrls: ['./productos.component.css']
})
export class ProductosComponent implements OnInit {
  productos: any[] = [];
  productoForm: FormGroup;
  editingProducto: any = null;
  showModal = false;

  constructor(private apiService: ApiService, private fb: FormBuilder) {
    this.productoForm = this.fb.group({
      nombre: ['', Validators.required],
      precio: ['', [Validators.required, Validators.min(0)]],
      stock: ['', [Validators.required, Validators.min(0)]]
    });
  }

  ngOnInit(): void {
    this.loadProductos();
  }

  loadProductos(): void {
    this.apiService.getProductos().subscribe({
      next: (data) => {
        this.productos = data;
      },
      error: (err) => {
        console.error('Error al cargar productos:', err);
      }
    });
  }

  openModal(producto?: any): void {
    this.editingProducto = producto || null;
    if (producto) {
      this.productoForm.patchValue(producto);
    } else {
      this.productoForm.reset();
    }
    this.showModal = true;
  }

  closeModal(): void {
    this.showModal = false;
    this.editingProducto = null;
  }

  saveProducto(): void {
    if (this.productoForm.valid) {
      if (this.editingProducto) {
        this.apiService.updateProducto(this.editingProducto.id, this.productoForm.value).subscribe({
          next: () => {
            this.loadProductos();
            this.closeModal();
          },
          error: (err) => {
            console.error('Error al actualizar producto:', err);
          }
        });
      } else {
        this.apiService.createProducto(this.productoForm.value).subscribe({
          next: () => {
            this.loadProductos();
            this.closeModal();
          },
          error: (err) => {
            console.error('Error al crear producto:', err);
          }
        });
      }
    }
  }

  deleteProducto(id: number): void {
    if (confirm('¿Está seguro de eliminar este producto?')) {
      this.apiService.deleteProducto(id).subscribe({
        next: () => {
          this.loadProductos();
        },
        error: (err) => {
          console.error('Error al eliminar producto:', err);
        }
      });
    }
  }
}
