/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { AvistamientoDeleteDialogComponent } from 'app/entities/avistamiento/avistamiento-delete-dialog.component';
import { AvistamientoService } from 'app/entities/avistamiento/avistamiento.service';

describe('Component Tests', () => {
  describe('Avistamiento Management Delete Component', () => {
    let comp: AvistamientoDeleteDialogComponent;
    let fixture: ComponentFixture<AvistamientoDeleteDialogComponent>;
    let service: AvistamientoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [AvistamientoDeleteDialogComponent]
      })
        .overrideTemplate(AvistamientoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AvistamientoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvistamientoService);
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
