import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { JefePrestamista } from '../models/JefePrestamista/JefePrestamista';
import { JefePrestamistaDTO } from '../models/JefePrestamista/JefePrestamistaDTO';
import { JefePrestamistaUpdateDTO } from '../models/JefePrestamista/JefePrestamistaUpdateDTO';

@Injectable({
  providedIn: 'root'
})
export class JefePrestamistaService {

  private apiUrl = 'http://localhost:8081/api/jefePrestamista';

  constructor(private http: HttpClient) { }

  getJefesPrestamistas(): Observable<JefePrestamista[]> {
    return this.http.get<JefePrestamista[]>(`${this.apiUrl}/findByAll`);
  }

  getJefePrestamista(id: number): Observable<JefePrestamista> {
    return this.http.get<JefePrestamista>(`${this.apiUrl}/findById/${id}`);
  }

  createJefePrestamista(jefePrestamistaDATA: JefePrestamistaDTO) : Observable<any> {
    return this.http.post(`${this.apiUrl}/jefePrestamistas`, jefePrestamistaDATA);
  }

  updateJefePrestamista(id: number, jefePrestamistaUpdateDTO: JefePrestamistaUpdateDTO) : Observable<any> {
    return this.http.put(`${this.apiUrl}/jefePrestamistas/${id}`, jefePrestamistaUpdateDTO);
  }

}
