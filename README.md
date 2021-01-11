## 2021 컴퓨터공학부 졸업작품   
### 주제 : Deep Learning을 적용한 CFRP(탄소 섬유 강화 플라스틱) 가공 결함 검출   

***   

### 팀 구성   
  한국산업기술대학교 컴퓨터공학부   
  - 조현민   
  - 황규빈   
  - 최성훈   
  - 이형석   

#### 프로그램 구성   
  1. 언어   
      > Python Keras Image Generator(CV Open Source, Python version)   
      > Java   
      > JSP   

  2. 운영체제   
      > Ubuntu Linux 16ver   
      > Windows 10 64bits   
      > AWS Server(instance 미확정)   

  3. 개발 툴   
      > Spring   
      > Pycharm   

#### 졸업작품 일정   
  - **2021.01.07일(목)**   
      - 발표 내용물   
      [제안서](https://github.com/kpuce2021/Hyeongdori/blob/master/1%EC%B0%A8/%ED%8C%80%ED%98%95%EB%8F%8C%EC%9D%B4_1%EC%B0%A8%EC%A0%9C%EC%95%88%EC%84%9C.pptx)   
      [요약 계획서](https://github.com/kpuce2021/Hyeongdori/blob/master/1%EC%B0%A8/%ED%8C%80%ED%98%95%EB%8F%8C%EC%9D%B4_%EC%A2%85%ED%95%A9%EC%84%A4%EA%B3%84_%EA%B3%84%ED%9A%8D%EC%84%9C.hwp)   

=======
## 딥러닝 알고리즘   
#### 1. 심층 신경망 DNN   
		입력층과 출력층 사이 다중의 은닉층을 포함하는 인공 신경망으로 비선형적 관계 학습 가능,
		학습을 위한 연산량과 과하게 학습하여 실제 데이터에 대해 오차가 증가하는 과적합, 기울기 값의
		손실 문제 등이 발생 가능하다.   
#### 2. 합성곱 신경망 CNN   
		최소한의 전처리를 사용하도록 설계된 다계층 퍼셉트론의 한 종류, 하나 또는 여러 개의 합성곱 
		계층과 그 위에 일반적 인공 신경망 계층들로 이루어짐, 2차원 구조의 입력 데이터 충분히 활용가능
		(2차원 데이터는, 사진 등)   
#### 3. 순환 신경망 RNN   
		인공 신경망을 구성하는 유닛 사이 연결이 지시회로를 구성하는 신경망, 임의 입력을 처리하기 위해 신경망 내부 메모리 활용 가능   
#### 4. 제한 볼츠만 머신 RBM   
		층간 연결을 없애면 머신은 가시 유닛과 은닉 유닛으로 이뤄진 무방향 이분 그래프 형태 모양이 됨, 추론에 대한 실용적 문제 적용에 기여함
   
#### 5. 심층 신뢰 신경망 DBN   
		그래프 생성 모형으로 잠재변수의 다중계층으로 이루어진 심층 신경만 의미, 생성 모형이라는 
		특성상 선행학습에 사용 가능, 가중치 미조정 가능, 훈련용 데이터가 적을 때 굉장히 유용하며 
		적을수록 가중치 초기값이 결과적인 모델에 끼치는 영향이 세진다.   

***   

## AWS로 GPU 기반의 딥러닝 학습 환경 구축하기   
  - 아마존 웹 서비스(AWS)를 이용하여 GPU 인스턴스를 이용한 딥러닝 학습 환경 만들기   
  - 출처 : https://hanseokhyeon.tistory.com/entry/AWS%EB%A1%9C-%EB%94%A5-%EB%9F%AC%EB%8B%9D-%EB%AA%A8%EB%8D%B8-%ED%95%99%EC%8A%B5%ED%95%98%EA%B8%B0   


#### 딥러닝이란?   
  1. 뉴럴넷 기반의 기계학습 기법   
  2. 학습 시 많은 연산을 필요로 하여 학습에 소요되는 시간이 많이 필요함   
  3. 단순 연산을 많이 하기 때문에 병렬처리에 특화된 GPU를 사용하여 학습 시간을 단축시킬 수 있음   
   
#### AWS 딥러닝 EC2란?   
		- 사용자가 가상 컴퓨터를 임대 받아 그 위에 자신만의 컴퓨터 어플리케이션들을 실행할 수 있게 함   
		- EC2는 사용자가 아마존 머신 이미지(AMI)로 부팅하여 인스턴스라 부르는 가상 머신과 원하는 소프트웨어를 포함하여 구성 가능하게 해주는 웹 서비스 제공   
		- 사용자는 필요에 의해 서버 인스턴스를 만들고 종료 가능   
		- 실행 중인 서버에 대해 시간 당 지불   
		- AWS Deep Learning Containers(DL containers)는 딥 러닝 프레임워크가 설치된 도커 이미지   
		- AWS DL Container를 사용하면 빠르게 Kubernetes나 EC2에 머신러닝을 더할 수 있음   
		
#### AWS 딥러닝 EC2 인스턴스 세부사항   
  [EC2 세부사항](https://aws.amazon.com/ko/ec2/?ec2-whats-new.sort-by=item.additionalFields.postDateTime&ec2-whats-new.sort-order=desc) .   

#### AWS EC2에서 TensorFlow model을 학습시키는 예제   
  [T 스토리](https://hanseokhyeon.tistory.com/entry/AWS%EB%A1%9C-%EB%94%A5-%EB%9F%AC%EB%8B%9D-%EB%AA%A8%EB%8D%B8-%ED%95%99%EC%8A%B5%ED%95%98%EA%B8%B0) .   
>>>>>>> LHS
