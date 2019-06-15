/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { AvistamientoUpdateComponent } from 'app/entities/avistamiento/avistamiento-update.component';
import { AvistamientoService } from 'app/entities/avistamiento/avistamiento.service';
import { Avistamiento } from 'app/shared/model/avistamiento.model';

describe('Component Tests', () => {
  describe('Avistamiento Management Update Component', () => {
    let comp: AvistamientoUpdateComponent;
    let fixture: ComponentFixture<AvistamientoUpdateComponent>;
    let service: AvistamientoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [AvistamientoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AvistamientoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AvistamientoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AvistamientoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Avistamiento(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new Avistamiento();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
