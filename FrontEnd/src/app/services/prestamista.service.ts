import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Prestamista } from '../models/Prestamista/Prestamista';
import { PrestamistaUpdateDTO } from '../models/Prestamista/PrestamistaUpdateDTO';
import { JefePrestamistaDTO } from '../models/JefePrestamista/JefePrestamistaDTO';

@Injectable({
  providedIn: 'root'
})
export class PrestamistaService {

  private apiUrl = 'http://localhost:8081/api/prestamista';

  constructor(private http: HttpClient) { }

  getPrestamistas() : Observable<Prestamista[]> {
    return this.http.get<Prestamista[]>(`${this.apiUrl}/findAll`);
  }

  getPrestamistasByJefe(id: number): Observable<Prestamista[]> {
    return this.http.get<Prestamista[]>(`${this.apiUrl}/findAllByJefePrestamistaId/${id}`)
  }

  getPrestamista(id: number): Observable<Prestamista> {
    return this.http.get<Prestamista>(`${this.apiUrl}/findById/${id}`)
  }

  addPrestamista(prestamistaDTO: JefePrestamistaDTO): Observable<Prestamista> {
    return this.http.post<Prestamista>(`${this.apiUrl}/prestamista`, prestamistaDTO);
  }

  updatePrestamista(id: number, prestamistaUpdateDTO: PrestamistaUpdateDTO) : Observable<any> {
    return this.http.put(`${this.apiUrl}/actualizarPrestamista/${id}`, prestamistaUpdateDTO);
  }

}
