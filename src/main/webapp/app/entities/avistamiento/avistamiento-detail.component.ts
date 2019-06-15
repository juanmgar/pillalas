import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IAvistamiento } from 'app/shared/model/avistamiento.model';

@Component({
  selector: 'jhi-avistamiento-detail',
  styles: ['agm-map { height: 300px; /* height is required */ }'],
  templateUrl: './avistamiento-detail.component.html'
})
export class AvistamientoDetailComponent implements OnInit {
  avistamiento: IAvistamiento;
  latitude;
  longitude;
  mapType = 'satellite';

  constructor(protected dataUtils: JhiDataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      this.avistamiento = avistamiento;
      this.latitude = Number(avistamiento.latitud);
      this.longitude = Number(avistamiento.longitud);
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
