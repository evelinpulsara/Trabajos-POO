import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service';

const API_URL = 'http://localhost:3000/api';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  constructor(private http: HttpClient, private authService: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.authService.getToken();
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': token || ''
    });
  }

  getOpcionesMenu(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/opciones/menu`, { headers: this.getHeaders() });
  }

  getOpciones(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/opciones`, { headers: this.getHeaders() });
  }

  createOpcion(opcion: any): Observable<any> {
    return this.http.post(`${API_URL}/opciones`, opcion, { headers: this.getHeaders() });
  }

  updateOpcion(id: number, opcion: any): Observable<any> {
    return this.http.put(`${API_URL}/opciones/${id}`, opcion, { headers: this.getHeaders() });
  }

  deleteOpcion(id: number): Observable<any> {
    return this.http.delete(`${API_URL}/opciones/${id}`, { headers: this.getHeaders() });
  }

  getClientes(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/clientes`, { headers: this.getHeaders() });
  }

  createCliente(cliente: any): Observable<any> {
    return this.http.post(`${API_URL}/clientes`, cliente, { headers: this.getHeaders() });
  }

  updateCliente(id: number, cliente: any): Observable<any> {
    return this.http.put(`${API_URL}/clientes/${id}`, cliente, { headers: this.getHeaders() });
  }

  deleteCliente(id: number): Observable<any> {
    return this.http.delete(`${API_URL}/clientes/${id}`, { headers: this.getHeaders() });
  }

  getProductos(): Observable<any[]> {
    return this.http.get<any[]>(`${API_URL}/productos`, { headers: this.getHeaders() });
  }

  createProducto(producto: any): Observable<any> {
    return this.http.post(`${API_URL}/productos`, producto, { headers: this.getHeaders() });
  }

  updateProducto(id: number, producto: any): Observable<any> {
    return this.http.put(`${API_URL}/productos/${id}`, producto, { headers: this.getHeaders() });
  }

  deleteProducto(id: number): Observable<any> {
    return this.http.delete(`${API_URL}/productos/${id}`, { headers: this.getHeaders() });
  }
}
