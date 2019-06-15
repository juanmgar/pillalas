import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Ave } from 'app/shared/model/ave.model';
import { AveService } from './ave.service';
import { AveComponent } from './ave.component';
import { AveDetailComponent } from './ave-detail.component';
import { AveUpdateComponent } from './ave-update.component';
import { AveDeletePopupComponent } from './ave-delete-dialog.component';
import { IAve } from 'app/shared/model/ave.model';

@Injectable({ providedIn: 'root' })
export class AveResolve implements Resolve<IAve> {
  constructor(private service: AveService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAve> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Ave>) => response.ok),
        map((ave: HttpResponse<Ave>) => ave.body)
      );
    }
    return of(new Ave());
  }
}

export const aveRoute: Routes = [
  {
    path: '',
    component: AveComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: [],
      defaultSort: 'id,asc',
      pageTitle: 'pillalasAlVueloApp.ave.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AveDetailComponent,
    resolve: {
      ave: AveResolve
    },
    data: {
      authorities: [],
      pageTitle: 'pillalasAlVueloApp.ave.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AveUpdateComponent,
    resolve: {
      ave: AveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.ave.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AveUpdateComponent,
    resolve: {
      ave: AveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.ave.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const avePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: AveDeletePopupComponent,
    resolve: {
      ave: AveResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'pillalasAlVueloApp.ave.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
