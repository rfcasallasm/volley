/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { LetterUpdateComponent } from 'app/entities/letter/letter-update.component';
import { LetterService } from 'app/entities/letter/letter.service';
import { Letter } from 'app/shared/model/letter.model';

describe('Component Tests', () => {
    describe('Letter Management Update Component', () => {
        let comp: LetterUpdateComponent;
        let fixture: ComponentFixture<LetterUpdateComponent>;
        let service: LetterService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [LetterUpdateComponent]
            })
                .overrideTemplate(LetterUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LetterUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LetterService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new Letter(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.letter = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new Letter();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.letter = entity;
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
