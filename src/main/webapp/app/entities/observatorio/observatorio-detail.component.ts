import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IObservatorio } from 'app/shared/model/observatorio.model';

@Component({
  selector: 'jhi-observatorio-detail',
  templateUrl: './observatorio-detail.component.html'
})
export class ObservatorioDetailComponent implements OnInit {
  observatorio: IObservatorio;

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ observatorio }) => {
      this.observatorio = observatorio;
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
