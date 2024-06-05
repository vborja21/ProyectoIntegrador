import { Component } from '@angular/core';
import { routes } from '../../dashboard-routing.module';
import { AuthService } from 'src/app/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent {

  public menuItems!: any[];

  constructor(private authService: AuthService, private router: Router) {}

  ngOnInit() {
    this.filterMenuItemsBasedOnRole();
  }

  filterMenuItemsBasedOnRole() {
    const roles = this.authService.getUserRole();
    this.menuItems = [
      { path: '/dashboard/inversionista', title: 'Inversionista', allowedRoles: ['Inversionista'] },
      { path: '/dashboard/jefePrestamista', title: 'Jefe Prestamista', allowedRoles: ['Inversionista'] },
      { path: '/dashboard/prestamista', title: 'Prestamista', allowedRoles: ['JefePrestamista'] },
      { path: '/dashboard/prestatario', title: 'Prestatario', allowedRoles: ['Prestamista'] }, // Prestamista puede ver Prestatario
      { path: '/dashboard/registrarPrestamo', title: 'Registro de Prestamo', allowedRoles: ['Prestatario'] }, // Prestamista puede ver Prestatario
      { path: '/dashboard/listarPrestamoPrestatario', title: 'Listado Prestamo Prestatario', allowedRoles: ['Prestatario'] }, // Prestamista puede ver Prestatario
      { path: '/dashboard/listarPrestamoPrestamista', title: 'Listado Prestamo Prestamista', allowedRoles: ['Prestamista'] } // Prestamista puede ver Prestatario
    ].filter(item => item.allowedRoles.some(role => roles.includes(role)));
  }

  logout(){
    this.authService.logout();
    this.router.navigate(['/']);
  }

}
