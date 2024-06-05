import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Rol } from '../models/rol';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class RolService {
  private url: string = "";

  private rolSubject = new BehaviorSubject<Rol[]>([]);
  roles$ = this.rolSubject.asObservable();

  private selectedRolSubject = new BehaviorSubject<Rol | null>(null);
  selectedRol$ = this.selectedRolSubject.asObservable();

  constructor(private httpClient: HttpClient) {
    this.loadRol();
   }
   private loadRol(){
    this.httpClient.get<Rol[]>(this.url).subscribe(res =>{
      this.rolSubject.next(res);
    });
   }
   setSelectRol(rol: Rol | null): void{
    console.log('Rol Seleccionado:', rol);
    this.selectedRolSubject.next(rol);
   }
}
