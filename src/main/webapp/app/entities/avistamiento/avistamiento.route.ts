import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Avistamiento } from 'app/shared/model/avistamiento.model';
import { AvistamientoService } from './avistamiento.service';
import { AvistamientoComponent } from './avistamiento.component';
import { AvistamientoDetailComponent } from './avistamiento-detail.component';
import { AvistamientoUpdateComponent } from './avistamiento-update.component';
import { AvistamientoDeletePopupComponent } from './avistamiento-delete-dialog.component';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';

@Injectable({ providedIn: 'root' })
export class AvistamientoResolve implements Resolve<IAvistamiento> {
  constructor(private service: AvistamientoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAvistamiento> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Avistamiento>) => response.ok),
        map((avistamiento: HttpResponse<Avistamiento>) => avistamiento.body)
      );
    }
    return of(new Avistamiento());
  }
}

export const avistamientoRoute: Routes = [
  {
    path: '',
    component: AvistamientoComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pillalasAlVueloApp.avistamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AvistamientoDetailComponent,
    resolve: {
      avistamiento: AvistamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.avistamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AvistamientoUpdateComponent,
    resolve: {
      avistamiento: AvistamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.avistamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AvistamientoUpdateComponent,
    resolve: {
      avistamiento: AvistamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.avistamiento.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const avistamientoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AvistamientoDeletePopupComponent,
    resolve: {
      avistamiento: AvistamientoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.avistamiento.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
