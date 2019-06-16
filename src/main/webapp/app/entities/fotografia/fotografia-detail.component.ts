import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFotografia } from 'app/shared/model/fotografia.model';
import { IUser, AccountService } from 'app/core';

@Component({
  selector: 'jhi-fotografia-detail',
  templateUrl: './fotografia-detail.component.html'
})
export class FotografiaDetailComponent implements OnInit {
  fotografia: IFotografia;
  currentAccount: IUser;
  canEdit: Boolean;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute, protected accountService: AccountService) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fotografia }) => {
      this.fotografia = fotografia;
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
