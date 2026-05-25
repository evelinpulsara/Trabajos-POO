import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Opcion } from '../models/opcion.model';

@Injectable({
  providedIn: 'root'
})
export class MenuService {
  private apiUrl = 'http://localhost:8080/api/opciones';

  constructor(private http: HttpClient) { }

  getMenuHierarchy(): Observable<Opcion[]> {
    return this.http.get<Opcion[]>(`${this.apiUrl}/menu`);
  }

  getAllOpciones(): Observable<Opcion[]> {
    return this.http.get<Opcion[]>(this.apiUrl);
  }
}
