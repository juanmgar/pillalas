import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Observatorio } from 'app/shared/model/observatorio.model';
import { ObservatorioService } from './observatorio.service';
import { ObservatorioComponent } from './observatorio.component';
import { ObservatorioDetailComponent } from './observatorio-detail.component';
import { ObservatorioUpdateComponent } from './observatorio-update.component';
import { ObservatorioDeletePopupComponent } from './observatorio-delete-dialog.component';
import { IObservatorio } from 'app/shared/model/observatorio.model';

@Injectable({ providedIn: 'root' })
export class ObservatorioResolve implements Resolve<IObservatorio> {
  constructor(private service: ObservatorioService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IObservatorio> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Observatorio>) => response.ok),
        map((observatorio: HttpResponse<Observatorio>) => observatorio.body)
      );
    }
    return of(new Observatorio());
  }
}

export const observatorioRoute: Routes = [
  {
    path: '',
    component: ObservatorioComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'pillalasAlVueloApp.observatorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ObservatorioDetailComponent,
    resolve: {
      observatorio: ObservatorioResolve
    },
    data: {
      authorities: [],
      pageTitle: 'pillalasAlVueloApp.observatorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ObservatorioUpdateComponent,
    resolve: {
      observatorio: ObservatorioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.observatorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ObservatorioUpdateComponent,
    resolve: {
      observatorio: ObservatorioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.observatorio.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const observatorioPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ObservatorioDeletePopupComponent,
    resolve: {
      observatorio: ObservatorioResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.observatorio.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
