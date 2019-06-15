import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAvistamiento } from 'app/shared/model/avistamiento.model';

@Component({
  selector: 'jhi-avistamiento-detail',
  templateUrl: './avistamiento-detail.component.html'
})
export class AvistamientoDetailComponent implements OnInit {
  avistamiento: IAvistamiento;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      this.avistamiento = avistamiento;
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
