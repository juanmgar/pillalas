import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IObservatorio, Observatorio } from 'app/shared/model/observatorio.model';
import { ObservatorioService } from './observatorio.service';
import { IUser, UserService } from 'app/core';
import { IAve } from 'app/shared/model/ave.model';
import { AveService } from 'app/entities/ave';

@Component({
  selector: 'jhi-observatorio-update',
  templateUrl: './observatorio-update.component.html'
})
export class ObservatorioUpdateComponent implements OnInit {
  observatorio: IObservatorio;
  isSaving: boolean;

  users: IUser[];

  aves: IAve[];

  editForm = this.fb.group({
    id: [],
    nombre: [null, [Validators.required]],
    latitud: [null, [Validators.required]],
    longitud: [null, [Validators.required]],
    descripcion: [],
    foto: [],
    fotoContentType: [],
    autor: [],
    aves: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected observatorioService: ObservatorioService,
    protected userService: UserService,
    protected aveService: AveService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ observatorio }) => {
      this.updateForm(observatorio);
      this.observatorio = observatorio;
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

  updateForm(observatorio: IObservatorio) {
    this.editForm.patchValue({
      id: observatorio.id,
      nombre: observatorio.nombre,
      latitud: observatorio.latitud,
      longitud: observatorio.longitud,
      descripcion: observatorio.descripcion,
      foto: observatorio.foto,
      fotoContentType: observatorio.fotoContentType,
      autor: observatorio.autor,
      aves: observatorio.aves
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
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

  clearInputImage(field: string, fieldContentType: string, idInput: string) {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null
    });
    if (this.elementRef && idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const observatorio = this.createFromForm();
    if (observatorio.id !== undefined) {
      this.subscribeToSaveResponse(this.observatorioService.update(observatorio));
    } else {
      this.subscribeToSaveResponse(this.observatorioService.create(observatorio));
    }
  }

  private createFromForm(): IObservatorio {
    const entity = {
      ...new Observatorio(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      latitud: this.editForm.get(['latitud']).value,
      longitud: this.editForm.get(['longitud']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fotoContentType: this.editForm.get(['fotoContentType']).value,
      foto: this.editForm.get(['foto']).value,
      autor: this.editForm.get(['autor']).value,
      aves: this.editForm.get(['aves']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IObservatorio>>) {
    result.subscribe((res: HttpResponse<IObservatorio>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
