/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { ObservatorioDeleteDialogComponent } from 'app/entities/observatorio/observatorio-delete-dialog.component';
import { ObservatorioService } from 'app/entities/observatorio/observatorio.service';

describe('Component Tests', () => {
  describe('Observatorio Management Delete Component', () => {
    let comp: ObservatorioDeleteDialogComponent;
    let fixture: ComponentFixture<ObservatorioDeleteDialogComponent>;
    let service: ObservatorioService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [ObservatorioDeleteDialogComponent]
      })
        .overrideTemplate(ObservatorioDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ObservatorioDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ObservatorioService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
