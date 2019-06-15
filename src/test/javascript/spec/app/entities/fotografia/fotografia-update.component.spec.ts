/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { FotografiaUpdateComponent } from 'app/entities/fotografia/fotografia-update.component';
import { FotografiaService } from 'app/entities/fotografia/fotografia.service';
import { Fotografia } from 'app/shared/model/fotografia.model';

describe('Component Tests', () => {
  describe('Fotografia Management Update Component', () => {
    let comp: FotografiaUpdateComponent;
    let fixture: ComponentFixture<FotografiaUpdateComponent>;
    let service: FotografiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [FotografiaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(FotografiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(FotografiaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FotografiaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Fotografia(123);
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
        const entity = new Fotografia();
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
