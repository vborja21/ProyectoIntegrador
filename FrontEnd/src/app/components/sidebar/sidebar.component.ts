import { Component } from '@angular/core';
import { Rol } from 'src/app/models/rol';
import { RolService } from 'src/app/services/rol.service';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {
  roles: Rol[] = [];
  
  constructor(private rolService: RolService){
    rolService.roles$.subscribe(res =>{
      this.roles = res;
    });
  }

/*Metodo para filtrar por Rol de acuerdo al log del usuario */


}
