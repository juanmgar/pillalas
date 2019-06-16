import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';
import { DomSanitizer, SafeResourceUrl } from '@angular/platform-browser';

import { IAve } from 'app/shared/model/ave.model';
import { AccountService } from 'app/core';

@Component({
  selector: 'jhi-ave-detail',
  templateUrl: './ave-detail.component.html'
})
export class AveDetailComponent implements OnInit {
  ave: IAve;
  srcData: SafeResourceUrl;

  constructor(
    protected dataUtils: JhiDataUtils,
    protected activatedRoute: ActivatedRoute,
    private sanitizer: DomSanitizer,
    protected accountService: AccountService
  ) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ ave }) => {
      this.ave = ave;
      this.srcData = this.sanitizer.bypassSecurityTrustResourceUrl('data:' + ave.sonidoContentType + ';base64,' + ave.sonido);
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

  isAuthenticated() {
    return this.accountService.isAuthenticated();
  }
}
