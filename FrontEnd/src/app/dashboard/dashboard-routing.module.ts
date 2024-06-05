import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { PrestatarioComponent } from './pages/prestatario/prestatario.component';
import { PrestamistaComponent } from './pages/prestamista/prestamista.component';
import { JefePrestamistaComponent } from './pages/jefe-prestamista/jefe-prestamista.component';
import { InversionistaComponent } from './pages/inversionista/inversionista.component';
import { DashboardComponent } from './dashboard.component';
import { AuthGuard } from '../guards/auth.guard';
import { RegistrarPrestamoComponent } from './pages/registrar-prestamo/registrar-prestamo.component';
import { ListarPrestamoPrestatarioComponent } from './pages/listar-prestamo-prestatario/listar-prestamo-prestatario.component';
import { ListarPrestamoPrestamistaComponent } from './pages/listar-prestamo-prestamista/listar-prestamo-prestamista.component';

export const routes: Routes = [
  {
    path: '',
    component: DashboardComponent,
    canActivate: [AuthGuard],
    children: [
      {path: 'prestatario', title: 'Prestatario' ,component: PrestatarioComponent},
      {path: 'prestamista', title: 'Prestamista' ,component: PrestamistaComponent},
      {path: 'jefePrestamista', title: 'Jefe Prestamista' , component: JefePrestamistaComponent},
      {path: 'inversionista', title: 'Inversionista' , component: InversionistaComponent},
      {path: 'registrarPrestamo', title: 'Registro de Prestamo' , component: RegistrarPrestamoComponent},
      {path: 'listarPrestamoPrestatario', title: 'Listado Prestamo Prestatario' , component: ListarPrestamoPrestatarioComponent},
      {path: 'listarPrestamoPrestamista', title: 'Listado Prestamo Prestamista' , component: ListarPrestamoPrestamistaComponent}
    ]

  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class DashboardRoutingModule { }
