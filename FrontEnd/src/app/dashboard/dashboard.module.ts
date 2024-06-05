import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { DashboardRoutingModule } from './dashboard-routing.module';
import { DashboardComponent } from './dashboard.component';
import { PrestamistaComponent } from './pages/prestamista/prestamista.component';
import { PrestatarioComponent } from './pages/prestatario/prestatario.component';
import { InversionistaComponent } from './pages/inversionista/inversionista.component';
import { JefePrestamistaComponent } from './pages/jefe-prestamista/jefe-prestamista.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegistrarPrestamoComponent } from './pages/registrar-prestamo/registrar-prestamo.component';
import { ListarPrestamoPrestatarioComponent } from './pages/listar-prestamo-prestatario/listar-prestamo-prestatario.component';
import { ListarPrestamoPrestamistaComponent } from './pages/listar-prestamo-prestamista/listar-prestamo-prestamista.component';


@NgModule({
  declarations: [
    DashboardComponent,
    PrestamistaComponent,
    PrestatarioComponent,
    InversionistaComponent,
    JefePrestamistaComponent,
    SidebarComponent,
    RegistrarPrestamoComponent,
    ListarPrestamoPrestatarioComponent,
    ListarPrestamoPrestamistaComponent
  ],
  imports: [
    CommonModule,
    DashboardRoutingModule,
    FormsModule,
    ReactiveFormsModule
  ]
})
export class DashboardModule { }
