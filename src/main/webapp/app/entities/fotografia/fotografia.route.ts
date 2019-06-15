import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Fotografia } from 'app/shared/model/fotografia.model';
import { FotografiaService } from './fotografia.service';
import { FotografiaComponent } from './fotografia.component';
import { FotografiaDetailComponent } from './fotografia-detail.component';
import { FotografiaUpdateComponent } from './fotografia-update.component';
import { FotografiaDeletePopupComponent } from './fotografia-delete-dialog.component';
import { IFotografia } from 'app/shared/model/fotografia.model';

@Injectable({ providedIn: 'root' })
export class FotografiaResolve implements Resolve<IFotografia> {
  constructor(private service: FotografiaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFotografia> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Fotografia>) => response.ok),
        map((fotografia: HttpResponse<Fotografia>) => fotografia.body)
      );
    }
    return of(new Fotografia());
  }
}

export const fotografiaRoute: Routes = [
  {
    path: '',
    component: FotografiaComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'pillalasAlVueloApp.fotografia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FotografiaDetailComponent,
    resolve: {
      fotografia: FotografiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.fotografia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FotografiaUpdateComponent,
    resolve: {
      fotografia: FotografiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.fotografia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FotografiaUpdateComponent,
    resolve: {
      fotografia: FotografiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.fotografia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const fotografiaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FotografiaDeletePopupComponent,
    resolve: {
      fotografia: FotografiaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.fotografia.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
