import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IFotografia, Fotografia } from 'app/shared/model/fotografia.model';
import { FotografiaService } from './fotografia.service';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';
import { AvistamientoService } from 'app/entities/avistamiento';
import { IObservatorio } from 'app/shared/model/observatorio.model';
import { ObservatorioService } from 'app/entities/observatorio';
import { IUser, UserService } from 'app/core';
import { IAve } from 'app/shared/model/ave.model';
import { AveService } from 'app/entities/ave';

@Component({
  selector: 'jhi-fotografia-update',
  templateUrl: './fotografia-update.component.html'
})
export class FotografiaUpdateComponent implements OnInit {
  fotografia: IFotografia;
  isSaving: boolean;

  avistamientos: IAvistamiento[];

  observatorios: IObservatorio[];

  users: IUser[];

  aves: IAve[];

  editForm = this.fb.group({
    id: [],
    nombre: [],
    fichero: [],
    ficheroContentType: [],
    avistamiento: [],
    observatorio: [],
    autor: [],
    aves: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected fotografiaService: FotografiaService,
    protected avistamientoService: AvistamientoService,
    protected observatorioService: ObservatorioService,
    protected userService: UserService,
    protected aveService: AveService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ fotografia }) => {
      this.updateForm(fotografia);
      this.fotografia = fotografia;
    });
    this.avistamientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAvistamiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAvistamiento[]>) => response.body)
      )
      .subscribe((res: IAvistamiento[]) => (this.avistamientos = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.observatorioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IObservatorio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IObservatorio[]>) => response.body)
      )
      .subscribe((res: IObservatorio[]) => (this.observatorios = res), (res: HttpErrorResponse) => this.onError(res.message));
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

  updateForm(fotografia: IFotografia) {
    this.editForm.patchValue({
      id: fotografia.id,
      nombre: fotografia.nombre,
      fichero: fotografia.fichero,
      ficheroContentType: fotografia.ficheroContentType,
      avistamiento: fotografia.avistamiento,
      observatorio: fotografia.observatorio,
      autor: fotografia.autor,
      aves: fotografia.aves
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
    const fotografia = this.createFromForm();
    if (fotografia.id !== undefined) {
      this.subscribeToSaveResponse(this.fotografiaService.update(fotografia));
    } else {
      this.subscribeToSaveResponse(this.fotografiaService.create(fotografia));
    }
  }

  private createFromForm(): IFotografia {
    const entity = {
      ...new Fotografia(),
      id: this.editForm.get(['id']).value,
      nombre: this.editForm.get(['nombre']).value,
      ficheroContentType: this.editForm.get(['ficheroContentType']).value,
      fichero: this.editForm.get(['fichero']).value,
      avistamiento: this.editForm.get(['avistamiento']).value,
      observatorio: this.editForm.get(['observatorio']).value,
      autor: this.editForm.get(['autor']).value,
      aves: this.editForm.get(['aves']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFotografia>>) {
    result.subscribe((res: HttpResponse<IFotografia>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackAvistamientoById(index: number, item: IAvistamiento) {
    return item.id;
  }

  trackObservatorioById(index: number, item: IObservatorio) {
    return item.id;
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
