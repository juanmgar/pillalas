import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService } from 'ng-jhipster';
import { IAvistamiento, Avistamiento } from 'app/shared/model/avistamiento.model';
import { AvistamientoService } from './avistamiento.service';
import { IFotografia } from 'app/shared/model/fotografia.model';
import { FotografiaService } from 'app/entities/fotografia';
import { IAve } from 'app/shared/model/ave.model';
import { AveService } from 'app/entities/ave';

@Component({
  selector: 'jhi-avistamiento-update',
  templateUrl: './avistamiento-update.component.html'
})
export class AvistamientoUpdateComponent implements OnInit {
  isSaving: boolean;

  fotografias: IFotografia[];

  aves: IAve[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    latitud: [null, [Validators.required]],
    longitud: [null, [Validators.required]],
    descripcion: [],
    fotos: [],
    aves: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected avistamientoService: AvistamientoService,
    protected fotografiaService: FotografiaService,
    protected aveService: AveService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      this.updateForm(avistamiento);
    });
    this.fotografiaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IFotografia[]>) => mayBeOk.ok),
        map((response: HttpResponse<IFotografia[]>) => response.body)
      )
      .subscribe((res: IFotografia[]) => (this.fotografias = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.aveService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAve[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAve[]>) => response.body)
      )
      .subscribe((res: IAve[]) => (this.aves = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(avistamiento: IAvistamiento) {
    this.editForm.patchValue({
      id: avistamiento.id,
      nombre: avistamiento.nombre,
      fecha: avistamiento.fecha != null ? avistamiento.fecha.format(DATE_TIME_FORMAT) : null,
      latitud: avistamiento.latitud,
      longitud: avistamiento.longitud,
      descripcion: avistamiento.descripcion,
      fotos: avistamiento.fotos,
      aves: avistamiento.aves
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const avistamiento = this.createFromForm();
    if (avistamiento.id !== undefined) {
      this.subscribeToSaveResponse(this.avistamientoService.update(avistamiento));
    } else {
      this.subscribeToSaveResponse(this.avistamientoService.create(avistamiento));
    }
  }

  private createFromForm(): IAvistamiento {
    const entity = {
      ...new Avistamiento(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      fecha: this.editForm.get(['fecha']).value != null ? moment(this.editForm.get(['fecha']).value, DATE_TIME_FORMAT) : undefined,
      latitud: this.editForm.get(['latitud']).value,
      longitud: this.editForm.get(['longitud']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fotos: this.editForm.get(['fotos']).value,
      aves: this.editForm.get(['aves']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAvistamiento>>) {
    result.subscribe((res: HttpResponse<IAvistamiento>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackFotografiaById(index: number, item: IFotografia) {
    return item.id;
  }

  trackAveById(index: number, item: IAve) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
