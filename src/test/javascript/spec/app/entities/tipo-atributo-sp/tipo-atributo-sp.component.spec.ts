/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SuperPoliticosTestModule } from '../../../test.module';
import { TipoAtributoSpComponent } from '../../../../../../main/webapp/app/entities/tipo-atributo-sp/tipo-atributo-sp.component';
import { TipoAtributoSpService } from '../../../../../../main/webapp/app/entities/tipo-atributo-sp/tipo-atributo-sp.service';
import { TipoAtributoSp } from '../../../../../../main/webapp/app/entities/tipo-atributo-sp/tipo-atributo-sp.model';

describe('Component Tests', () => {

    describe('TipoAtributoSp Management Component', () => {
        let comp: TipoAtributoSpComponent;
        let fixture: ComponentFixture<TipoAtributoSpComponent>;
        let service: TipoAtributoSpService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SuperPoliticosTestModule],
                declarations: [TipoAtributoSpComponent],
                providers: [
                    TipoAtributoSpService
                ]
            })
            .overrideTemplate(TipoAtributoSpComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoAtributoSpComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoAtributoSpService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TipoAtributoSp(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.tipoAtributos[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
