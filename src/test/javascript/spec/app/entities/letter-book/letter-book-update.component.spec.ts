/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { VolleyTestModule } from '../../../test.module';
import { LetterBookUpdateComponent } from 'app/entities/letter-book/letter-book-update.component';
import { LetterBookService } from 'app/entities/letter-book/letter-book.service';
import { LetterBook } from 'app/shared/model/letter-book.model';

describe('Component Tests', () => {
    describe('LetterBook Management Update Component', () => {
        let comp: LetterBookUpdateComponent;
        let fixture: ComponentFixture<LetterBookUpdateComponent>;
        let service: LetterBookService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [VolleyTestModule],
                declarations: [LetterBookUpdateComponent]
            })
                .overrideTemplate(LetterBookUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(LetterBookUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LetterBookService);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity', fakeAsync(() => {
                // GIVEN
                const entity = new LetterBook(123);
                spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.letterBook = entity;
                // WHEN
                comp.save();
                tick(); // simulate async

                // THEN
                expect(service.update).toHaveBeenCalledWith(entity);
                expect(comp.isSaving).toEqual(false);
            }));

            it('Should call create service on save for new entity', fakeAsync(() => {
                // GIVEN
                const entity = new LetterBook();
                spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                comp.letterBook = entity;
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
