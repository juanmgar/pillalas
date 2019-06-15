import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IFotografia } from 'app/shared/model/fotografia.model';

@Component({
  selector: 'jhi-fotografia-detail',
  templateUrl: './fotografia-detail.component.html'
})
export class FotografiaDetailComponent implements OnInit {
  fotografia: IFotografia;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fotografia }) => {
      this.fotografia = fotografia;
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
}
