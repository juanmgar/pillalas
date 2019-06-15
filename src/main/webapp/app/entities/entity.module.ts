import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'observatorio',
        loadChildren: './observatorio/observatorio.module#PillalasAlVueloObservatorioModule'
      },
      {
        path: 'ave',
        loadChildren: './ave/ave.module#PillalasAlVueloAveModule'
      },
      {
        path: 'avistamiento',
        loadChildren: './avistamiento/avistamiento.module#PillalasAlVueloAvistamientoModule'
      },
      {
        path: 'fotografia',
        loadChildren: './fotografia/fotografia.module#PillalasAlVueloFotografiaModule'
      },
      {
        path: 'observatorio',
        loadChildren: './observatorio/observatorio.module#PillalasAlVueloObservatorioModule'
      },
      {
        path: 'ave',
        loadChildren: './ave/ave.module#PillalasAlVueloAveModule'
      },
      {
        path: 'avistamiento',
        loadChildren: './avistamiento/avistamiento.module#PillalasAlVueloAvistamientoModule'
      },
      {
        path: 'fotografia',
        loadChildren: './fotografia/fotografia.module#PillalasAlVueloFotografiaModule'
      },
      {
        path: 'observatorio',
        loadChildren: './observatorio/observatorio.module#PillalasAlVueloObservatorioModule'
      },
      {
        path: 'ave',
        loadChildren: './ave/ave.module#PillalasAlVueloAveModule'
      },
      {
        path: 'avistamiento',
        loadChildren: './avistamiento/avistamiento.module#PillalasAlVueloAvistamientoModule'
      },
      {
        path: 'fotografia',
        loadChildren: './fotografia/fotografia.module#PillalasAlVueloFotografiaModule'
      },
      {
        path: 'observatorio',
        loadChildren: './observatorio/observatorio.module#PillalasAlVueloObservatorioModule'
      },
      {
        path: 'ave',
        loadChildren: './ave/ave.module#PillalasAlVueloAveModule'
      },
      {
        path: 'avistamiento',
        loadChildren: './avistamiento/avistamiento.module#PillalasAlVueloAvistamientoModule'
      },
      {
        path: 'fotografia',
        loadChildren: './fotografia/fotografia.module#PillalasAlVueloFotografiaModule'
      },
      {
        path: 'observatorio',
        loadChildren: './observatorio/observatorio.module#PillalasAlVueloObservatorioModule'
      },
      {
        path: 'ave',
        loadChildren: './ave/ave.module#PillalasAlVueloAveModule'
      },
      {
        path: 'avistamiento',
        loadChildren: './avistamiento/avistamiento.module#PillalasAlVueloAvistamientoModule'
      },
      {
        path: 'fotografia',
        loadChildren: './fotografia/fotografia.module#PillalasAlVueloFotografiaModule'
      },
      {
        path: 'observatorio',
        loadChildren: './observatorio/observatorio.module#PillalasAlVueloObservatorioModule'
      },
      {
        path: 'ave',
        loadChildren: './ave/ave.module#PillalasAlVueloAveModule'
      },
      {
        path: 'avistamiento',
        loadChildren: './avistamiento/avistamiento.module#PillalasAlVueloAvistamientoModule'
      },
      {
        path: 'fotografia',
        loadChildren: './fotografia/fotografia.module#PillalasAlVueloFotografiaModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class PillalasAlVueloEntityModule {}
