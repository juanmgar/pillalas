import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IAve, Ave } from 'app/shared/model/ave.model';
import { AveService } from './ave.service';
import { IObservatorio } from 'app/shared/model/observatorio.model';
import { ObservatorioService } from 'app/entities/observatorio';
import { IAvistamiento } from 'app/shared/model/avistamiento.model';
import { AvistamientoService } from 'app/entities/avistamiento';

@Component({
  selector: 'jhi-ave-update',
  templateUrl: './ave-update.component.html'
})
export class AveUpdateComponent implements OnInit {
  isSaving: boolean;

  observatorios: IObservatorio[];

  avistamientos: IAvistamiento[];

  editForm = this.fb.group({
    id: [],
    nombreComun: [null, [Validators.required]],
    nombreCientifico: [null, [Validators.required]],
    descripcion: [],
    foto: [],
    fotoContentType: [],
    sonido: [],
    sonidoContentType: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected aveService: AveService,
    protected observatorioService: ObservatorioService,
    protected avistamientoService: AvistamientoService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ ave }) => {
      this.updateForm(ave);
    });
    this.observatorioService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IObservatorio[]>) => mayBeOk.ok),
        map((response: HttpResponse<IObservatorio[]>) => response.body)
      )
      .subscribe((res: IObservatorio[]) => (this.observatorios = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.avistamientoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IAvistamiento[]>) => mayBeOk.ok),
        map((response: HttpResponse<IAvistamiento[]>) => response.body)
      )
      .subscribe((res: IAvistamiento[]) => (this.avistamientos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(ave: IAve) {
    this.editForm.patchValue({
      id: ave.id,
      nombreComun: ave.nombreComun,
      nombreCientifico: ave.nombreCientifico,
      descripcion: ave.descripcion,
      foto: ave.foto,
      fotoContentType: ave.fotoContentType,
      sonido: ave.sonido,
      sonidoContentType: ave.sonidoContentType
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
    const ave = this.createFromForm();
    if (ave.id !== undefined) {
      this.subscribeToSaveResponse(this.aveService.update(ave));
    } else {
      this.subscribeToSaveResponse(this.aveService.create(ave));
    }
  }

  private createFromForm(): IAve {
    const entity = {
      ...new Ave(),
      id: this.editForm.get(['id']).value,
      nombreComun: this.editForm.get(['nombreComun']).value,
      nombreCientifico: this.editForm.get(['nombreCientifico']).value,
      descripcion: this.editForm.get(['descripcion']).value,
      fotoContentType: this.editForm.get(['fotoContentType']).value,
      foto: this.editForm.get(['foto']).value,
      sonidoContentType: this.editForm.get(['sonidoContentType']).value,
      sonido: this.editForm.get(['sonido']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAve>>) {
    result.subscribe((res: HttpResponse<IAve>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackObservatorioById(index: number, item: IObservatorio) {
    return item.id;
  }

  trackAvistamientoById(index: number, item: IAvistamiento) {
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
