/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { PillalasAlVueloTestModule } from '../../../test.module';
import { AveUpdateComponent } from 'app/entities/ave/ave-update.component';
import { AveService } from 'app/entities/ave/ave.service';
import { Ave } from 'app/shared/model/ave.model';

describe('Component Tests', () => {
  describe('Ave Management Update Component', () => {
    let comp: AveUpdateComponent;
    let fixture: ComponentFixture<AveUpdateComponent>;
    let service: AveService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [PillalasAlVueloTestModule],
        declarations: [AveUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(AveUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AveUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AveService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Ave(123);
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
        const entity = new Ave();
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
