import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAvistamiento } from 'app/shared/model/avistamiento.model';

@Component({
  selector: 'jhi-avistamiento-detail',
  templateUrl: './avistamiento-detail.component.html'
})
export class AvistamientoDetailComponent implements OnInit {
  avistamiento: IAvistamiento;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      this.avistamiento = avistamiento;
    });
  }

  previousState() {
    window.history.back();
  }
}
