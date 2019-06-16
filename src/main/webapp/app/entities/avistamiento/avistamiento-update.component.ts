import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAvistamiento, Avistamiento } from 'app/shared/model/avistamiento.model';
import { AvistamientoService } from './avistamiento.service';
import { IUser, UserService } from 'app/core';
import { IAve } from 'app/shared/model/ave.model';
import { AveService } from 'app/entities/ave';

@Component({
  selector: 'jhi-avistamiento-update',
  styles: ['agm-map { height: 300px; /* height is required */ }'],
  templateUrl: './avistamiento-update.component.html'
})
export class AvistamientoUpdateComponent implements OnInit {
  avistamiento: IAvistamiento;
  isSaving: boolean;
  latitude: Number;
  longitude: Number;
  mapType = 'satellite';

  users: IUser[];

  aves: IAve[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    fecha: [null, [Validators.required]],
    descripcion: [],
    autor: [],
    aves: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected avistamientoService: AvistamientoService,
    protected userService: UserService,
    protected aveService: AveService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ avistamiento }) => {
      this.updateForm(avistamiento);
      this.avistamiento = avistamiento;
      if (this.avistamiento.latitud == undefined) {
        this.latitude = 43.524163;
        this.longitude = -5.615905;
      } else {
        this.latitude = Number(avistamiento.latitud);
        this.longitude = Number(avistamiento.longitud);
      }
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
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
      descripcion: avistamiento.descripcion,
      autor: avistamiento.autor,
      aves: avistamiento.aves
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  onChooseLocation(event) {
    this.latitude = event.coords.lat;
    this.longitude = event.coords.lng;
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const avistamiento = this.createFromForm();
    avistamiento.latitud = this.latitude.toString();
    avistamiento.longitud = this.longitude.toString();
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
      descripcion: this.editForm.get(['descripcion']).value,
      autor: this.editForm.get(['autor']).value,
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

  trackUserById(index: number, item: IUser) {
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
