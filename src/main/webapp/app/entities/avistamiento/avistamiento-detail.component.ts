import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAvistamiento } from 'app/shared/model/avistamiento.model';
import { AccountService } from 'app/core';

@Component({
  selector: 'jhi-avistamiento-detail',
  styles: ['agm-map { height: 300px; /* height is required */ }'],
  templateUrl: './avistamiento-detail.component.html'
})
export class AvistamientoDetailComponent implements OnInit {
  avistamiento: IAvistamiento;
  latitude: Number;
  longitude: Number;
  mapType = 'satellite';
  currentAccount;
  IUser;
  canEdit: boolean;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected accountService: AccountService) {}

  ngOnInit() {
    this.canEdit = false;
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      this.avistamiento = avistamiento;
      this.latitude = Number(avistamiento.latitud);
      this.longitude = Number(avistamiento.longitud);
    });
    this.accountService.identity().then(account => {
      this.currentAccount = account;
      this.canEditorDelete();
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }
  previousState() {
    window.history.back();
  }

  canEditorDelete() {
    if (this.currentAccount.authorities.includes('ROLE_ADMIN')) {
      this.canEdit = true;
    } else {
      this.canEdit == false;
    }
  }
}
